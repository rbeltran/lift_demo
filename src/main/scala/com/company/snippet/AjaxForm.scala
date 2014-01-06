package com.company 
package snippet 

import scala.xml.{NodeSeq, Text}
import net.liftweb._
import java.util.Date
import com.company.lib._
// import Helpers._
import http._
import common._
import util.Helpers._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq


object AjaxForm {
	// def render( in: NodeSeq ) : NodeSeq = {
	def render = {
		println( "Got Ajax Call" )
		var name = ""
		var age = "0"
		val whence = S.referer openOr "/ajax.html"

		// our process method returns a
		// JsCmd which will be sent back to the browser
		// as part of the response
		def process(): JsCmd= {
			// sleep to allow the user to see the spinning icon
			Thread.sleep(400)
			// do the matching
			asInt(age) match {
			// display an error and otherwise do nothing
			case Full(a) if a < 13 => S.error("age", "Too young!"); Noop
			case Full(a) => {
			RedirectTo(whence, () => {
			S.notice("Name: "+name)
			S.notice("Age: "+a)
			})
			}
			// more errors
			case _ => S.error("age", "Age doesn't parse as a number"); Noop
			}
		}
		"name=name" #> SHtml.text(name, name = _, "id" -> "the_name") &
		"name=age" #> (SHtml.text(age, age = _) ++ SHtml.hidden(process))
	}
}

