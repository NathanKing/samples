class RenameQuestionResponsesToSubmissions < ActiveRecord::Migration
  def change
      rename_table :question_responses, :submissions
  end     
end
