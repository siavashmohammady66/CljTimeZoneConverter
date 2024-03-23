(ns clock.events
  (:require
   [re-frame.core :as re-frame]
   [clock.db :as db]
   [clock.logic :as logic]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

;
(re-frame/reg-event-db
 :calculate-time
 (fn [db [_ {:keys [input-time input-timezone output-timezone]}] ]
   (assoc db :output-time
                   (logic/calculate-output-time
                    input-time
                    input-timezone
                    output-timezone))))
