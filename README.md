# streamnotify
A discord bot, that sends notifications at the start of a twitch live stream for multiple twitch streamers.

## installation
+ **prerequisites:**
  + Java 16 #
  + Twitch clientID # 
  + Twitch clientSecret # 
  + Discord botToken #
  + Discord channelID #


+ download the jar #
+ run the jar for the first time or manually create a config file
+ edit the config #
+ run the jar and enjoy it `java -jar ./streamnotify.jar` #

## example config
```
!!de.leonbcode.streamnotify.Config
#Discord
botToken: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
channelID: 000000000000000000

#Twitch
clientID: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
clientSecret: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
streamers:
  - streamer1
  - streamer2
  - streamer3
```