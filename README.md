# Sputnik Music Scraper [![Build Status](https://travis-ci.org/gardncl/scala-sputnik-scraper.svg?branch=master)](https://travis-ci.org/gardncl/scala-sputnik-scraper)

[Sputnik Music] is my go to resource for keeping a pulse on new music, storing my ratings for albums, and finding new bands to listen to. I have found that their website does not do a good job of showing top level data. I am hoping to scrape their entire website and use that information for some data science experiments I want to run.

 * For example on my profile I have an 'objectivity' score of 68%; is this based on comparisons to other users? Is this based on some formula they created years ago? What does 'objectivity' mean?
 * Given a set of users who like a modern genre (ex: indie rock) what albums and genres from decades previous do they all seem to like? Does their taste in older music seem to differ from
 * When new albums first come out generally their rating is a couple points above what it eventually ends up at. For example an album like 'Science Fiction' that Brand New came out with in 2017 had a rating of around 4.3, but now sits at 4.1. Is it possible to estimate how much an album's rating will drop?

 These are just a few of many questions I have regarding the data on Sputnik Music and as I dig into it further I'm sure I will discover more questions.

Example parse from a page:
```scala
Rating(4.5,Mastodon_2007,Some(2007-08-20))
Rating(4.0,Prometherion,Some(2007-08-12))
Rating(4.5,Racinette,Some(2007-08-12))
Rating(3.5,EvilFiek,Some(2007-08-09))
Rating(4.0,OpethFreak,Some(2007-08-08))
Rating(3.0,Goku,Some(2007-08-08))
Rating(5.0,dhowns,Some(2007-07-31))
Rating(4.5,teamsleep698,Some(2007-03-29))
Rating(5.0,entruce,Some(2006-04-04))
Rating(4.0,Thor,Some(2005-08-31))
Rating(4.0,BTBAM1390,None)
Rating(4.0,Mulciber,None)
Rating(4.5,Kye,None)
Rating(4.5,mexirocker666,None)
Rating(4.0,amcmillon,None)
Rating(3.5,HangEmHigh,None)
```

[Sputnik Music]:https://www.sputnikmusic.com/
[my profile]:https://www.sputnikmusic.com/user/gardncl

[![Build Status](https://travis-ci.org/tbeede/truenews.svg?branch=master)](https://travis-ci.org/tbeede/truenews)