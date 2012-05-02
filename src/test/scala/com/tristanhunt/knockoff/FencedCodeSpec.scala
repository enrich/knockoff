package com.tristanhunt.knockoff
import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FencedCodeSpec extends ChunkParser with Spec with ShouldMatchers {
  describe("ChunkParser") {
    it("should handle fenced code") {
      val src = "````\r\nThis is code\r\n````\r\n"
      parse(chunk, src).get should equal {
        FencedCodeChunk(src)
      }
    }
  }
}