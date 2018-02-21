package parsers

import models.SoundOffId
import org.joda.time.LocalDate
import org.scalatest._

class ProcessRatingTest extends FunSpec {

  private final val soundOffId = SoundOffId(1)

  describe("Parses profiles and ratings correctly") {

    describe("with single word name") {
      val name = "SCREAM"
      val classic = 5.0

      it("no review") {
        val line = "5.0 classic    SCREAM"
        val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
        assert(rating.date === None && profile.userName === name && rating.rating === classic)
      }

      describe("with contributor tag") {
        it("no review") {
          val line = "5.0 classic     SCREAM STAFF"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === None && profile.userName === name && rating.rating === classic)
        }
      }
    }

    describe("with multi word name") {
      val multiWordName = "former sputnik's home post-punk maester"
      val veryPoor = 1.5

      it("no review") {
        val line = "1.5 very poor    former sputnik's home post-punk maester"
        val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
        assert(rating.date === None
          && profile.userName === multiWordName
          && rating.rating === veryPoor)
      }

      describe("with contributor tag") {
        it("no review") {
          val line = "1.5 very poor    former sputnik's home post-punk maester STAFF"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === None
            && profile.userName === multiWordName
            && rating.rating === veryPoor)
        }
      }
    }

    describe("with date") {
      describe("with single word name") {
        val name = "WhatAreTheTodds"
        val average = 2.5
        val date = Some(new LocalDate(2017, 9, 27))

        it("no review") {
          val line = "2.5 average    WhatAreTheTodds | September 27th 17"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === date
            && profile.userName === name
            && rating.rating === average)
        }

        it("with review") {
          val line = "2.5 average    upagainstthewall | April 10th 09 Muse reach perfection on their 3rd full length release with the stunning 'Absolution.' This album highlights everything that is good about the amazing UK trio. The album opens with the fantastic 'Apocolypse Please' and does not let you go until the very end track 'Ruled By Secrecy.' The album contains no filler tracks with every song being spectacular. 4 Bumps"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === Some(new LocalDate(2009, 4, 10))
            && profile.userName === "upagainstthewall"
            && rating.rating === average)
        }

        describe("with contributor tag") {
          it("no review") {
            val line = "2.5 average    WhatAreTheTodds | September 27th 17"
            val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
            assert(rating.date === date
              && profile.userName === name
              && rating.rating === average)
          }

          it("with review") {
            val line = "2.5 average    WhatAreTheTodds | September 27th 17 average at best"
            val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
            assert(rating.date === date
              && profile.userName === name
              && rating.rating === average)
          }
        }
      }

      describe("with multi word name") {
        val multiWordName = "resident sputbirb with fancy hat"
        val excellent = 4.0
        val date = Some(new LocalDate(2009, 3, 13))

        it("no review") {
          val line = "4.0 excellent    resident sputbirb with fancy hat | March 13th 09"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === date
            && profile.userName === multiWordName
            && rating.rating === excellent)
        }

        it("with review") {
          val line = "4.0 excellent    resident sputbirb with fancy hat | March 13th 09 their best work"
          val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
          assert(rating.date === date
            && profile.userName === multiWordName
            && rating.rating === excellent)
        }

        describe("with contributor tag") {
          it("no review") {
            val line = "4.0 excellent    resident sputbirb with fancy hat EMERITUS | March 13th 09"
            val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
            assert(rating.date === date
              && profile.userName === multiWordName
              && rating.rating === excellent)
          }

          it("with review") {
            val line = "4.0 excellent    resident sputbirb with fancy hat EMERITUS | March 13th 09 their best work"
            val (rating, profile) = ProcessRating.parseLineToRatingAndProfile((line, soundOffId))
            assert(rating.date === date
              && profile.userName === multiWordName
              && rating.rating === excellent)
          }
        }
      }
    }

  }


}
