class GradesController < ApplicationController
  def index

    @courses = current_user.courses
    
    respond_to do |format|
      format.html # index.html.erb
      format.json { render :json => { :var1 => @courses, :var2 => @course_titles, :var3 => @course_gradeds, :var4 => @course_out_ofs, :var5 => @course_weights, :var6 => @course_points, :var7 => @course_users } }
    end    
  end
end
