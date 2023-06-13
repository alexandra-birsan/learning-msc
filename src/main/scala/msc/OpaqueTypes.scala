package msc

// https://www.youtube.com/watch?v=8-b2AoctkiY&list=PLmtsMNDRU0BwsVUbhsH2HMqDMPNhQ0HPc&index=4
// the equivalent in Scala 2 is NewType https://github.com/estatico/scala-newtype (oh, wow! Finally I see why I used it in my previous work project!)

object OpaqueTypes {

  /*
  Motivation: we often define new types as wrappers over existing types. However, in many cases this involves some sort of overhead.
   */
//  case class Name(value: String) // this is a wrapper over String
//  {
//    // additional logic: check refined types
//  }

  object SocialNetwork { // "domain"

    // Name is a String, it does not wrap a String
    opaque type Name = String
    // opaque = a new modifier in Scala 3
    // the benefit of an opaque type is that we can treat it as standalone type (???)

    // opaque type = new type that we can access from the outside only using the API

    // 1. companion object
    object Name {
      def fromString(s: String): Option[Name] =
        if (s.isEmpty || s.charAt(0).isLower) None else Some(s) // simplified
    }

    // 2. extension methods
    extension (n: Name) {
      def length: Int =
        n.length // on the String class, we can do this here because we are in the scope where the Name opaque type was defined
    }
  }

  import SocialNetwork.Name

  // this does not compile because outside the scope the opaque type was declared, Name and String have 0 connections to one another :D
  // outside the scope Name != String
//  val name: Name = "Alex"

  /** cons of opaque types: you lose the API of the wrapped type pros: you start
    * from a clean slate, so you have access only to the API you defined allows
    * more flexibility over what you want to express
    */

  object Graphics {
    opaque type Color = Int // in hex
    opaque type Filter <: Color = Int // bounded type alias

    val Red: Color = 0xff000000
    val Green: Color = 0x00ff0000
    val Blue: Color = 0x0000ff00
    val halfTransparency: Filter = 0x88 // 50% transparency
  }

  import Graphics._
  case class OverlayFilter(c: Color)

  val fadeLayer = OverlayFilter(halfTransparency) // because Filter <: Color

  def main(args: Array[String]): Unit = {
    val nameOption = Name.fromString("Alex") // Some("Alex")
    nameOption.foreach(println)
//    nameOption.map(_.charAt(0)) // we do not have access to the String methods from here, we need to define an API with extension methods
    val alexNameLengthOption = nameOption.map(_.length)
    alexNameLengthOption.foreach(println)
  }

}
