(ns clock.logic
  (:require
   [clojure.string :as str]
   ))

(defn split-string-to-int [time]
  (map #(js/parseInt %) (str/split time #":")))

(defn calculate-minutes [[hour minute]]
  (+ (* hour 60) minute))

(defn get-hour-from-daily-minutes [daily-minutes]
  (let [corrected_daily-minutes (if (< daily-minutes 0) (+ daily-minutes 1440) daily-minutes)
        hours (int (/ corrected_daily-minutes 60))]
    (cond 
      (>= hours 24) (- hours 24)
      (< hours 0) (+ hours 24)
      :else
      hours)))

(defn add-zero-if-less-than-10 [number]
  (if (< number 10)
    (str "0" number)
    (str number)))

(defn calculate-output-time
  [input-time input-time-zone output-time-zone]
  (let [input-time-split (split-string-to-int input-time)
        input-time-daily-minutes (calculate-minutes input-time-split)
        time-zone-dev (- output-time-zone input-time-zone)
        output-time-daily-minutes (+ input-time-daily-minutes (* 60 time-zone-dev) )
        result-hours (get-hour-from-daily-minutes output-time-daily-minutes)
        result-minutes (mod output-time-daily-minutes 60)  
        ]
    (str (add-zero-if-less-than-10 (mod result-hours 24)) ":" (add-zero-if-less-than-10 result-minutes))))