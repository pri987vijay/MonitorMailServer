package engine

import javax.mail.internet.MimeBodyPart
import javax.mail.search.FlagTerm
import javax.mail._

import org.apache.commons.net.ftp.{FTPClient}


object MailsFunctions {
  /**
    * connectMailServer : connectes to gmail
    * @param emailAddress
    * @param emailPassword
    * @param readFolder :which folder it should get ike Inbox,Sent etc
    * @return Folder
    */
  def connectMailServer(emailAddress:String,emailPassword:String,readFolder:String): (Folder,com.sun.mail.imap.IMAPSSLStore) = {

    val props = System.getProperties()
    props.setProperty("mail.store.protocol", "imaps")
    val session = Session.getDefaultInstance(props, null)
    val store = session.getStore("imaps").asInstanceOf[com.sun.mail.imap.IMAPSSLStore]
    var inbox: Folder = null
    try{
      store.connect("imap.gmail.com", emailAddress, emailPassword)
      inbox = store.getFolder(readFolder) //readFolder is like 'Inbox'
      inbox.open(Folder.READ_ONLY)
      println("Connection with gmail is stablished successfully")
    }
    catch {
      case e:Exception=> println("not able to connect to mail server")
    }

    return (inbox,store)
  }
  def disconnectMailServer(store:com.sun.mail.imap.IMAPSSLStore,inbox:Folder) = {
    inbox.close(true)
    store.close()
    println("disconnected from mail server successfully")
  }

  /**connectFTP: creates FTP connection
    * host: IP of the machine eg :192.24.2.157
    * userName: username
    * password : corresponding password*/
  def connectFTP(host:String,userName:String,password:String): FTPClient ={
      val client:FTPClient = new FTPClient()
      try {
        client.connect(host)
        client.login(userName, password)
        println("FTP connection is created")
      }
      catch {
        case e:Exception => println("FTP connection error: "+e)

      }
        return client
      }

  def disconnectFTP(client:FTPClient)={
    client.logout()
    client.disconnect()
  }
  def getMessagesFromFolder(inbox:Folder):Array[Message] ={
    var messages:Array[Message]=null
    val seen = new Flags(Flags.Flag.SEEN)
    val unseenFlagTerm = new FlagTerm(seen,false)
    messages = inbox.search(unseenFlagTerm)
    println("Total number of unread messages:"+messages.length)
    return messages
  }

  def getMessagesWithHighestUID(inbox:Folder,highUID:Int)   ={
     /** has to be implement*/
  }

  /**
    * downloadAttachment: process one by one mail, check if it has csv attachment and store on the ftp location mentioned
    * @param messages : Array of unseen messages
    * @param client ftp client to store csv file
    * @param destlocation location for destination file on ftp
    */
  def downloadAttachment(messages:Array[Message],client:FTPClient,destlocation:String) = {

    for(message <- messages){
//      println("test"+message.getMessageNumber)
      if(message.isMimeType("multipart/*")){
//        println("has attachment:"+message.getSubject)
        val multipart:Multipart = message.getContent().asInstanceOf[Multipart]
//        println( multipart.getCount)
        for(i:Int <- 0 to multipart.getCount-1){
          val bodyPart = multipart.getBodyPart(i)
          val disposition = bodyPart.getDisposition()
          if(disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
            if(bodyPart.getFileName.contains(".csv")) {
              val mbp:MimeBodyPart = bodyPart.asInstanceOf[MimeBodyPart]
              val destFilePath:String = destlocation + bodyPart.getFileName()
              client.storeFile( destFilePath,mbp.getInputStream())

//              below two lines are to store file in local
//              mbp.saveFile(destFilePath)
//              mbp.getInputStream()

              println("Attachment is saved successfully")

            }
          }
        }
      }

    }

  }

}