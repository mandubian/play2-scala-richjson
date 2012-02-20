package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    import play.api.libs.json._
    import play.tools.richjson.RichJson._
    import play.api.libs.json.Json._
    
    val oldVersion = 
      JsObject(Seq(
          ("key1", JsObject(Seq(
        		  ("key11", JsString("alpha11")),
        		  ("key12", JsString("alpha12"))
          	))
          ),
          ("key2", JsObject(Seq(
        		  ("key21", JsNumber(23)),
        		  ("key22", JsBoolean(true))
          	))
          ),
          ("key3", JsObject(Seq(
              ("key31", JsArray(List(
                  JsString("key311"), 
                  JsNumber(67.23),
                  JsString("key313"),
                  JsObject(Seq(
                      ("key311", JsString("alpha311")),
                      ("key312", JsString("alpha312"))
                      ))
                ))
              )
            ))  
          )
      ))
           
    val newVersion = 
      "key1" \: ( 
          "key11" \: "alpha11" ++ 
          "key12" \: "alpha12"
      ) ++ 
      "key2" \: (
          "key21" \: 23 ++
          "key22" \: true
      ) ++
      "key3" \: (
          "key31" \: ( 
              "key311" @@ 
              67.23 @@ 
              "key313" @@ 
              (   "key311" \: "alpha311" ++ 
                  "key312" \: "alpha312" ) )
      )
    
      
    assert(newVersion == oldVersion,  "Not equal")
    assert(oldVersion \ "key2" == ("key21"\:23 ++ "key22"\:true), "Not equal")

    Ok(toJson(newVersion))
  }
  
}