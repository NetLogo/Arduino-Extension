package arduino

import scala.util.{ Either, Left, Try }

object MessageParser {
  def parseEntry(entry: String): Either[ErrorRecord, ValuePair] = {
    val fields = entry.split(",")
    val valueField =
      if (fields.length == 3) Right(fields(2))
      else {
        val accumulatedContent = fields.drop(2).foldLeft(List.empty[String]) {
          case (hd::tl, s) if hd.endsWith("\\") => (hd.dropRight(1) + "," + s) :: tl
          case (acc, s) => s :: acc
        }
        if (accumulatedContent.length == 1)
          Right(accumulatedContent.head)
        else
          Left(ErrorRecord(entry, "Arduino values must have three comma-separated fields", None))
      }

    valueField.map(content => (fields(0).toLowerCase, fields(1).toUpperCase.head, content))
      .flatMap {
        case (name, 'S', rawValue) =>
          Right(ValuePair(name, rawValue))
        case (name, 'D', rawValue) =>
          Try(rawValue.toDouble)
            .fold(
            {
              case e: Exception => Left(ErrorRecord(entry, "Cannot parse number", Some(e)))
              case t: Throwable => throw t
            },
            d => Right(ValuePair(name, Double.box(d))))
              case (name, tpe, rawValue) =>
                Left(ErrorRecord(entry, s"Unknown type '$tpe' for value '$name'", None))
      }
  }
}
