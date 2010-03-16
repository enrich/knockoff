package com.tristanhunt.knockoff

import java.io.{ File }
import scala.util.logging.ConsoleLogger

object DefaultDiscounter extends Discounter with ConsoleLogger {
  def main( args : Array[ String ] ) : Unit = try {
    if ( args.contains("--version") ) {
      Console.err.print( "DefaultDiscounter " )
    }
    if ( args.contains("--version") || args.contains("-shortversion") ) {
      Console.err.println( "0.6.1-6" )
      return 0
    }
    
    if ( args.isEmpty ) {
      val sb = new StringBuilder
      var line : String = null
      do {
        line = Console.readLine
        if ( line != null ) sb.append( line )
      } while ( line != null )
      println( toXHTML( knockoff( sb.toString ) ).toString )
    } else {
      args.filter( _ != "--html4tags" ).foreach { fileName =>
        println( toXHTML( knockoff( readText( fileName ) ) ).toString )
      }
    }
  } catch {
    case th : Throwable => {
      th.printStackTrace( Console.err )
    }
  }
  
  private def readText( fileName : String ) : String =
    io.Source.fromFile( new File( fileName ) ).mkString("")
}