(ns clock.views
  (:require
   [re-frame.core :as re-frame]
   [clock.subs :as subs]
   [reagent.core :as r]))

(defn validate-form [form-state]
  (or (not (number? (js/parseFloat (get form-state :input-timezone))))
      (= (get form-state :input-timezone) "")
      (= (get form-state :output-timezone) "")
      (nil? (get form-state :input-time nil))
      (not (number? (js/parseFloat (get form-state :output-timezone))))))

(defn form-component []
  (let [form-state (r/atom {:input-timezone 5 :output-timezone -3})
        is-invalid validate-form
        submit-form (fn [form-data]
                      (re-frame/dispatch [:calculate-time (js->clj form-data :keywordize-keys true)]))]
        
    (fn []
      (let [current-form-state @form-state
            is-disabled (is-invalid @form-state) ]
        [:form {:onSubmit (fn [event]
                            (.preventDefault event)
                            (submit-form current-form-state))
                :class "bg-gray-100 p-8 rounded-lg"}
         [:input {:type "time"
                  :value (get current-form-state :input-time nil)
                  :onChange #(do (js/console.log "time changed" (.. % -target -value))
                                 (swap! form-state assoc :input-time (.. % -target -value)))
                  :class "w-full px-2 py-1 mb-2 rounded border border-gray-200 shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"}]
         [:input {:type "number" :min "-12" :max "14" :step "0.5"
                  :value (get current-form-state :input-timezone 5)
                  :onChange #(swap! form-state assoc :input-timezone (.. % -target -value))
                  :class "w-full px-2 py-1 mb-2 rounded border border-gray-200 shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"}]
         [:input {:type "number" :min "-12" :max "14" :step "0.5"
                  :value (get current-form-state :output-timezone -3)
                  :onChange #(swap! form-state assoc :output-timezone (.. % -target -value))
                  :class "w-full px-2 py-1 mb-2 rounded border border-gray-200 shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"}]
         [:button {:type "submit" :disabled (is-invalid @form-state) 
                   :class (str "w-full bg-purple-600 text-white py-2 px-4 rounded hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2" (if is-disabled " opacity-50 cursor-not-allowed" ""))} 
          "Calculate"]]))))

(defn main-panel []
  (let [output-time (re-frame/subscribe [::subs/output-time])]
    [:div {:class "flex flex-col items-center justify-center h-screen"}
     [:h1 {:class "text-4xl font-bold mb-4"}
      "Clock"]
     [form-component]
     [:h1
      {:class "text-2xl font-bold mt-4"}
      "Output time " @output-time]]))
     
