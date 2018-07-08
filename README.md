# MonitorMailServer
A project to monitor a mail account, that will monitor all mails from a fixed mail account, also it will check if that mail is having any attachments. If the attachment is having CSV file, read it and save it to FTP location.

## Getting Started

-download git repository '
https://github.com/pri987vijay/MonitorMailServer.git

# Project Title


### Prerequisites

- install sbt

### Installing

1) Clone the git repository (path mention above)
2) Open mails.mailConfig.conf file
  a) Update sourceMailConf object - source of the data
    i) emailAddress = enter gmail address
    ii) emailPassword = enter gmail password
    iii) readFolder = enter the folder name of gmail, where from we need to download the messages. example: 'Inbox'
    iv) monitorTimeInMinutes = enter time interval in minute/s, we need to monitor the gmail index. 
  b) Update destinationFTPConf object - destination to download the attachment
    i) hostIP= IP address of FTP server/local system
    ii) userName= username of the FTP server/local system
    iii) password= password of the FTP server/local system
    iv) destlocation= download attachment destination. example: /home/user/..
3) Go the the project location in the terminal and execute 'sbt run'

## Running the tests

Explain how to run the automated tests for this system

## Deployment

Add additional notes about how to deploy this on a live system

## Authors

* **Priyanka Vijay** - *Initial work* - (https://github.com/pri987vijay)
