class HomeController < ApplicationController
  
  def index
    
    @mycourses = Course.all
    @show_dialog = (cookies[:last_seen_dialog] == nil || Time.now.to_f - cookies[:last_seen_dialog].to_f > 1800)

    respond_to do |format|
      
      format.html # index.html.erb
      format.json { render :json => { :var1 => @mycourses } }
    end
    
    session[:has_seen_dialog] = true;
    cookies[:last_seen_dialog] = Time.now.to_f

  end

  def courses
    @courses = current_user.courses
  end
  
  def add_course
    
  end
  
  def create
    @user = current_user
    
    course_ids = params[:courses]
    unless course_ids.blank?
      course_ids.each do |course_id|
        course = Course.find(course_id)
        if @user.enrolled?(course) == false then
          @user.courses << course
        end
        
      end
    end
    redirect_to "/home/courses"
  end
  
  def remove_course
    @user = current_user
    
    course_ids = params[:courses]
    
    unless course_ids.blank?
      course_ids.each do |course_id|
        course = Course.find(course_id)
        if @user.enrolled?(course)
          @user.courses.delete(course)
        end
        
      end
    end    

  end
  
  def remove_courses
    
    for course in current_user.courses do
      current_user.courses.delete(course)
    end
    
    redirect_to "/home/courses"
  end
end
