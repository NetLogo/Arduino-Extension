package arduino

import org.scalatest.{ FunSuite, Inside }

class MessageParserTest extends FunSuite with Inside {
  testInvalidFieldCount("")
  testInvalidFieldCount("abc,")
  testInvalidFieldCount("abc,s,def,bar")
  testInvalidFieldCount("abc,s,def\\,bar,qux")
  testInvalidFieldCount("abc,s,def\\,\\,bar,qux")

  test("returns an error when the entry is of an unknown type") {
    assertInvalid("abc,J,qux", "Unknown type 'J' for value 'abc'")
  }

  test("parses a double") {
    assertValid("abc", Double.box(123), "abc,D,123")
    assertValid("abc", Double.box(123), "ABC,d,123")
  }

  test("returns an error when it can't parse an unparseable double") {
    inside(MessageParser.parseEntry("A,D,ABC")) {
      case Left(ErrorRecord("A,D,ABC", "Cannot parse number", Some(e))) =>
        assert(e.getMessage == """For input string: "ABC"""")
    }
  }

  test("parses a string") {
    assertValid("abc", "def", "abc,s,def")
    assertValid("abc", "def", "ABC,S,def")
  }

  testParsesString("d,ef", "d\\,ef")
  testParsesString("d,e,f", "d\\,e\\,f")
  testParsesString("d\\f", "d\\f")

  def assertInvalid(entry: String, desc: String, expectedException: Option[Exception] = None) = {
    assertResult(Left(ErrorRecord(entry, desc, expectedException)))(
      MessageParser.parseEntry(entry))
  }

  def assertValid(key: String, value: AnyRef, entry: String): Unit = {
    assertResult(Right(ValuePair(key, value)))(MessageParser.parseEntry(entry))
  }

  def testParsesString(expected: String, stringEntry: String): Unit = {
    test(s"parses '$stringEntry' to string value '$expected'") {
      val fullEntry = s"abc,s,${stringEntry}"
      assertValid("abc", expected, fullEntry)
    }
  }

  def testInvalidFieldCount(entry: String): Unit = {
    test(s"returns an error for the entry '$entry'") {
      assertInvalid(entry, "Arduino values must have three comma-separated fields")
    }
  }
}

