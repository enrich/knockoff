package com.tristanhunt.knockoff
import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import java.io.StringWriter
import scala.xml.XML
import scala.xml.Node

/**
 * testing for fenced code.
 */
@RunWith(classOf[JUnitRunner])
class FencedCodeSpec extends ChunkParser with Spec with ShouldMatchers {
  describe("ChunkParser") {
    it("should handle fenced code with embedded backticks") {
      val src = "```\r\nThis is `code`\r\n\r\nMore ``` code\r\n```\r\n"
      parse(chunk, src).get should equal {
        FencedCodeChunk("", "This is `code`\r\n\r\nMore ``` code\r\n")
      }
    }
    it("should handle fenced code with language option like GFM") {
      val src = "```ruby\r\nThis is `code`\r\n\r\nMore ``` code\r\n```\r\n"
      parse(chunk, src).get should equal {
        FencedCodeChunk("ruby", "This is `code`\r\n\r\nMore ``` code\r\n")
      }
    }
    it("should handle fenced code with lotsa options") {
      val src = "```fig=TRUE echo=FALSE blah blah blah\r\nThis is `code`\r\n\r\nMore ``` code\r\n```\r\n"
      parse(chunk, src).get should equal {
        FencedCodeChunk("fig=TRUE echo=FALSE blah blah blah", "This is `code`\r\n\r\nMore ``` code\r\n")
      }
    }
  }

  def writeString(node: Node): String = {
    val sw = new StringWriter
    XML.write(sw, node, "UTF-8", false, null)
    sw.toString
  }

  describe("rendering") {
    import DefaultDiscounter._
    it("should render as html") {
      val src = "```\r\nThis is `code`\r\n\r\nMore ``` code\r\n```\r\n"
      writeString(toXHTML(knockoff(src))) should equal { "<pre><code>This is `code`\r\n\r\nMore ``` code\r\n</code></pre>" }
    }
    it("should ignore all the options") {
      // it's for a domain-specific implementation (outside this module)
      // to do whatever transformation is desired based on the options.
      val src = "```ruby\r\nThis is `code`\r\n\r\nMore ``` code\r\n```\r\n"
      writeString(toXHTML(knockoff(src))) should equal { "<pre><code>This is `code`\r\n\r\nMore ``` code\r\n</code></pre>" }
    }
  }
}