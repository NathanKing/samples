class CreateCourses < ActiveRecord::Migration
  def change
    create_table :courses do |t|
      t.string :course_name
      t.text :course_description

      t.timestamps
    end
  end
end
