class AddGradedAndPointsToQuestionResponses < ActiveRecord::Migration
  def change
    add_column :question_responses, :graded, :boolean

    add_column :question_responses, :points, :float

  end
end
