class CreateQuestions < ActiveRecord::Migration
  def change
    create_table :questions do |t|
      t.integer :points
      t.text :question
      t.references :assignment

      t.timestamps
    end
    add_index :questions, :assignment_id
  end
end
