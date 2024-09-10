object StudentManagementSystem {
  case class Student(name: String, grades: List[Int])
  def addStudent(students: List[Student], student: Student): List[Student] = {
    students :+ student
  }
  def averageGrade(student: Student): Double = {
    if (student.grades.isEmpty) 0.0
    else student.grades.sum.toDouble / student.grades.size
  }
  def printStudentAverages(students: List[Student]): Unit = {
    students.foreach { student =>
      println(s"${student.name}: Average Grade = ${averageGrade(student)}")
    }
  }
  def main(args: Array[String]): Unit = {
    var students: List[Student] = List()

    students = addStudent(students, Student("Alice", List(85, 92, 78)))
    students = addStudent(students, Student("Bob", List(90, 88, 84)))
    students = addStudent(students, Student("Charlie", List(70, 80, 90)))
    printStudentAverages(students)
  }
  
}
