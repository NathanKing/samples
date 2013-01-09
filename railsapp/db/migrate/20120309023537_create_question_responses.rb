class CreateQuestionResponses < ActiveRecord::Migration
  def change
    create_table :question_responses do |t|
      t.string :response
      t.references :user
      t.references :question


      t.timestamps
    end
    add_index :question_responses, :user_id
    add_index :question_responses, :question_id
  end
end
