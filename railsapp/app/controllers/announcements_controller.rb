class AnnouncementsController < ApplicationController
  
  def show
    @course = Course.find(params[:course_id])
    @announcement = @course.announcements.find(params[:id])
  end

  def create
    @course = Course.find(params[:course_id])
    @announcement = @course.announcements.create(params[:announcement])
    
    respond_to do |format|
      if @announcement.save
        format.html { redirect_to @course, notice: "Announcement created successfully." }
        format.json { render json: @announcement, status: :created, location: @announcement}      
      else 
        format.html { render action :"new"}
        format.json { render json: @announcement.errors, status: :unprocessable_entity}
      end
    end
    
  end
  
  def new
    @announcement = Announcement.new
    @course = Course.find(params[:course_id])
    
    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @announcement }
    end
  end

  def edit
    @course = Course.find(params[:course_id])
    @announcement = Announcement.find(params[:id])
  end
  def update
    @course = Course.find(params[:course_id])
    @announcement = Announcement.find(params[:id])
    
    respond_to do |format|
      if @announcement.update_attributes(params[:announcement])
        format.html { redirect_to(course_announcement_path(@announcement.course, @announcement), notice: "Announcement editted successfully.") }
      else
        format.html { render action :"edit"}
      end
    end
  end
  
end