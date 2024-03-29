# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20120323132441) do

  create_table "announcements", :force => true do |t|
    t.string   "text"
    t.integer  "course_id"
    t.string   "title"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "announcements", ["course_id"], :name => "index_announcements_on_course_id"

  create_table "assignments", :force => true do |t|
    t.string   "title"
    t.text     "description"
    t.integer  "value"
    t.datetime "due"
    t.integer  "course_id"
    t.datetime "created_at",  :null => false
    t.datetime "updated_at",  :null => false
  end

  add_index "assignments", ["course_id"], :name => "index_assignments_on_course_id"

  create_table "courses", :force => true do |t|
    t.string   "course_name"
    t.text     "course_description"
    t.datetime "created_at",         :null => false
    t.datetime "updated_at",         :null => false
  end

  create_table "questions", :force => true do |t|
    t.integer  "points"
    t.text     "question"
    t.integer  "assignment_id"
    t.datetime "created_at",    :null => false
    t.datetime "updated_at",    :null => false
  end

  add_index "questions", ["assignment_id"], :name => "index_questions_on_assignment_id"

  create_table "submissions", :force => true do |t|
    t.string   "response"
    t.integer  "user_id"
    t.integer  "question_id"
    t.datetime "created_at",  :null => false
    t.datetime "updated_at",  :null => false
    t.boolean  "graded"
    t.float    "points"
    t.string   "feedback"
  end

  add_index "submissions", ["question_id"], :name => "index_question_responses_on_question_id"
  add_index "submissions", ["user_id"], :name => "index_question_responses_on_user_id"

  create_table "users", :force => true do |t|
    t.string   "email",                  :default => "", :null => false
    t.string   "encrypted_password",     :default => "", :null => false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          :default => 0
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.datetime "created_at",                             :null => false
    t.datetime "updated_at",                             :null => false
    t.string   "role"
    t.string   "username"
    t.boolean  "has_seen_home"
    t.boolean  "has_seen_courses"
    t.boolean  "has_seen_grades"
  end

  add_index "users", ["email"], :name => "index_users_on_email", :unique => true
  add_index "users", ["reset_password_token"], :name => "index_users_on_reset_password_token", :unique => true

end
