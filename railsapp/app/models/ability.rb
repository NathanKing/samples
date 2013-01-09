class Ability

    # Define abilities for the passed in user here. For example:
    #
    #   user ||= User.new # guest user (not logged in)
    #   if user.admin?
    #     can :manage, :all
    #   else
    #     can :read, :all
    #   end
    #
    # The first argument to `can` is the action you are giving the user permission to do.
    # If you pass :manage it will apply to every action. Other common actions here are
    # :read, :create, :update and :destroy.
    #
    # The second argument is the resource the user can perform the action on. If you pass
    # :all it will apply to every resource. Otherwise pass a Ruby class of the resource.
    #
    # The third argument is an optional hash of conditions to further filter the objects.
    # For example, here the user can only update published articles.
    #
    #   can :update, Article, :published => true
    #
    # See the wiki for details: https://github.com/ryanb/cancan/wiki/Defining-Abilities
  include CanCan::Ability

  
  def initialize(user)  
   #:admin => "admin" 
   #:teacher => "teacher" 
   #:student => "student" 
   
    user ||= User.new # guest user (not logged in)
    
    if user.role?("admin")
     can :manage, :all
    elsif user.role?("teacher")
     can [:create, :update, :destroy], [Assignment]
     can [:create, :read, :update], [Course]
     can [:create, :update, :destroy], [Announcement]
    else user.role?("student")
      can :read, :all
    end
   end
end
