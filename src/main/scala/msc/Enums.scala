package msc

// https://www.youtube.com/watch?v=QnrXubpILu4&list=PLmtsMNDRU0BwsVUbhsH2HMqDMPNhQ0HPc&index=2
object Enums {

  // Scala 3 has first-class enums
  enum Permissions {
    case READ, WRITE, EXEC, NONE
  }

  // the compiler creates
  /*
  sealed class Permissions
    READ, WRITE, EXEC, NONE as constants
   */
  // enums are now first-class
  val read: Permissions = Permissions.READ

  // define enums with arguments !!
  enum PermissionsWithBits(val bits: Int) {
    case READ extends PermissionsWithBits(4) // 100
    case WRITE extends PermissionsWithBits(2) // 010
    case EXEC extends PermissionsWithBits(1) // 001
    case NONE extends PermissionsWithBits(0) // 000

    def toHex: String = Integer.toHexString(bits)
    // can create variables, don't recommend :D
  }

  // companion objects as for any regular class
  object PermissionsWithBits {

    // smart constructor
    def fromBits(bits: Int): PermissionsWithBits =
      PermissionsWithBits.NONE // do bit checking
  }

  val read2: PermissionsWithBits = PermissionsWithBits.READ
  val bitstring =
    read2.bits // we need to put val in enum PermissionsWithBits(val bits: Int) for this to work
  val hexString =
    read2.toHex // like you'd call a method on any kind of instance

  // standard API
  val first = Permissions.READ.ordinal // the index of the enum value
  val allPermisions = Permissions.values // array with all the possible values
  val readPermission = Permissions.valueOf(
    "READ"
  ) // Permissions.READ; throws  java.lang.ExceptionInInitializerError for Permissions.valueOf("hello")

  def main(args: Array[String]): Unit = {
    allPermisions.foreach(println)
    println(readPermission)
  }
}
