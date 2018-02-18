package io

case class ParseException(message: String) extends RuntimeException(message: String)
