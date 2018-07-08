package engine

import javax.mail.{Folder, Message}

import org.apache.commons.net.ftp.FTPClient


object Mails{
  def mailProcess(appConfigMap:Map[String,String])={

    // establish connection from gmail
    val (inbox,store):(Folder,com.sun.mail.imap.IMAPSSLStore) = MailsFunctions.connectMailServer(appConfigMap("emailAddress"),appConfigMap("emailPassword"),appConfigMap("readFolder"))
    if(store.isConnected)
      {
        //get unread messages
        val messages:Array[Message] = MailsFunctions.getMessagesFromFolder(inbox)

        //create ftp client to store attachments
        val client:FTPClient= MailsFunctions.connectFTP(appConfigMap("ftpHostIP"),appConfigMap("ftpUserName"),appConfigMap("ftpPassword"))

        if(client.isConnected){
          // Download csv attachemet for fetched unseen messages(emails)
          if(messages.length!=0) {MailsFunctions.downloadAttachment(messages,client,appConfigMap("ftpDestlocation"))}
          //disconnect ftp client and mail server
//          MailsFunctions.disconnectFTP(client)
        }
        else
        {
          println("not able to connect ftp server, so cant store files")
        }


        MailsFunctions.disconnectMailServer(store,inbox)

      }


  }
}
