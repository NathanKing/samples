class AddHasSeenHomeAndHasSeenCoursesAndHasSeenGradesToUser < ActiveRecord::Migration
  def change
    add_column :users, :has_seen_home, :boolean

    add_column :users, :has_seen_courses, :boolean

    add_column :users, :has_seen_grades, :boolean

  end
end
