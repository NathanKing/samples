class Question < ActiveRecord::Base
  belongs_to :assignment
  has_many :submissions
  
  accepts_nested_attributes_for :submissions, :reject_if => lambda { |a| a[:response].blank? }, :allow_destroy => true


  
  def submitted?(user)
    self.submissions.any? { |s| (s.response != nil and s.response != "") and s.user == user }
  end
  
  def graded?(user)
    self.submissions.any? { |s| s.graded and  s.user == user }
  end

  def grade(user)
    self.submissions.find_all { |s| s.graded and s.user == user }.first.points
  end
end
