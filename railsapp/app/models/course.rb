class Course < ActiveRecord::Base
  has_and_belongs_to_many :users
  belongs_to :user
  has_many :assignments
  has_many :announcements
  
  def professor
    self.users.find_by_role("teacher")
  end

  def students
    self.users.find_all { |u| u.role?("student") }
  end
  
  def grade(user)
    self.assignments.find_all { |a| a.graded?(user) }.collect { |a| a.grade(user) * a.value }.sum / self.assignments.collect { |a| a.value }.sum
  end
  
  def max_grade(user)
    self.assignments.find_all { |a| a.due < Date.today and a.graded?(user) }.collect { |a| a.value }.sum
  end
  
end
