class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :token_authenticatable, :encryptable, :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  # Setup accessible (or protected) attributes for your model
  attr_accessible :email, :password, :password_confirmation, :remember_me, :role, :username, :has_seen_home, :has_seen_courses, :has_seen_grades
  
  has_and_belongs_to_many :courses
  
  has_many :question_responses
  
  ROLES = %w[teacher student]
  
  
  def role?(string)
    self.role.eql? string
  end

  def enrolled?(course)
    self.courses.include?(course)
  end
  
  def unenrolled_courses
    Course.find(:all) - self.courses
  end

end
