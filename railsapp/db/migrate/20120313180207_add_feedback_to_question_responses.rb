class AddFeedbackToQuestionResponses < ActiveRecord::Migration
  def change
    add_column :question_responses, :feedback, :string

  end
end
