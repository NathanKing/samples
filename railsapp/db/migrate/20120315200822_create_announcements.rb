class CreateAnnouncements < ActiveRecord::Migration
  def change
    create_table :announcements do |t|
      t.string :text
      t.references :course
      t.string :title

      t.timestamps :created_at
    end
    add_index :announcements, :course_id
  end
end
