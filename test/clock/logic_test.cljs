(ns clock.logic-test
  (:require [cljs.test :refer-macros [deftest is are testing]]
            [clock.logic :refer [split-string-to-int calculate-minutes get-hour-from-daily-minutes add-zero-if-less-than-10 calculate-output-time]]))

(deftest split-string-to-int-test
  (testing "Splitting a string into integers"
    (is (= [1 2 3] (split-string-to-int "1:2:3")))
    (is (= [10 20 30] (split-string-to-int "10:20:30")))
    (is (= [0 0 0] (split-string-to-int "0:0:0")))))

(deftest calculate-minutes-test
  (testing "Calculating minutes from hour and minute"
    (is (= 0 (calculate-minutes [0 0])))
    (is (= 60 (calculate-minutes [1 0])))
    (is (= 90 (calculate-minutes [1 30])))
    (is (= 1439 (calculate-minutes [23 59])))))

(deftest get-hour-from-daily-minutes-test
  (testing "Getting hour from daily minutes"
    (is (= 0 (get-hour-from-daily-minutes 0)))
    (is (= 1 (get-hour-from-daily-minutes 60)))
    (is (= 1 (get-hour-from-daily-minutes 90)))
    (is (= 23 (get-hour-from-daily-minutes 1439)))
    (is (= 23 (get-hour-from-daily-minutes -1)))
    (is (= 23 (get-hour-from-daily-minutes -60)))
    (is (= 22 (get-hour-from-daily-minutes -90)))))

(deftest add-zero-if-less-than-10-test
  (testing "Adding zero if number is less than 10"
    (is (= "00" (add-zero-if-less-than-10 0)))
    (is (= "05" (add-zero-if-less-than-10 5)))
    (is (= "10" (add-zero-if-less-than-10 10)))
    (is (= "99" (add-zero-if-less-than-10 99)))))

(deftest calculate-output-time-test
  (testing "Calculating output time"
    (is (= "13:00" (calculate-output-time "12:00" 0 1)))
    (is (= "12:00" (calculate-output-time "12:00" 0 0)))
    (is (= "11:00" (calculate-output-time "12:00" 0 -1)))
    (is (= "00:00" (calculate-output-time "12:00" 0 -12)))
    (is (= "12:00" (calculate-output-time "12:00" 0 24)))
    (is (= "12:00" (calculate-output-time "12:00" 0 48)))
    (is (= "12:00" (calculate-output-time "12:00" 0 -24)))))