class AssignmentsController < ApplicationController
  def index
    @course = Course.find(params[:course_id])
    @assignments = @course.assignments
  end
  
  def show
    @course = Course.find(params[:course_id])
    @assignment = @course.assignments.find(params[:id])
  end

  def new
    @course = Course.find(params[:course_id])
    @assignment = Assignment.new
    
    1.times do
      question = @assignment.questions.build
      1.times { question.submissions.build }
    end

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @assignment }
    end
  end

  def create
    @course = Course.find(params[:course_id])
    @assignment = @course.assignments.create(params[:assignment])
    
    respond_to do |format|
      if @assignment.save
        format.mobile {redirect_to @course, flash: "Assignment created successfully."}
        format.html { redirect_to @course, notice: "Assignment created successfully." }
        format.json { render json: @assignment, status: :created, location: @assignment}      
      else 
        format.html { render action :"new"}
        format.json { render json: @assignment.errors, status: :unprocessable_entity}
      end
    end
  end
  
  def edit
    @course = Course.find(params[:course_id])
    @assignment = Assignment.find(params[:id])
    
    if params[:type] == 'submit'
      @questions = @assignment.questions
      @questions.each do |question|
        question.submissions.build
      end
    end
      
  end
  def update
    @course = Course.find(params[:course_id])
    @assignment = Assignment.find(params[:id])

    respond_to do |format|
      if @assignment.update_attributes(params[:assignment])
        format.html { redirect_to(course_assignment_path(@assignment.course, @assignment), notice: "Assignment editted successfully.") }
      else
        format.html { render action :"edit"}
      end
    end
  end
  
end