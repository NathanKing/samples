class Assignment < ActiveRecord::Base
  belongs_to :course
  has_many :questions, :dependent => :destroy
  
  accepts_nested_attributes_for :questions, :reject_if => lambda { |a| a[:question].blank? or a[:points].blank? }, :allow_destroy => true


  
  def submitted?(user)
    self.questions.length > 0 and self.questions.all? { |q| q.submitted?(user) } 
  end

  def graded?(user)
    self.questions.length > 0 and self.questions.all? { |q| q.graded?(user) }
  end
  def answer_key_provided?
    self.questions.length > 0 and self.submitted?(self.course.professor) 
  end
  
  def grade(user)
    (self.questions.collect { |q| q.grade(user) }.sum / self.questions.collect { |q| q.points }.sum * 1000).round / 10.0
  end
end
