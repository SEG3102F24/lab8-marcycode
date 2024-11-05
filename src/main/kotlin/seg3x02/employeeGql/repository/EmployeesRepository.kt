package seg3x02.employeeGql.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import seg3x02.employeeGql.entity.Employee

@Repository
interface EmployeeRepository : MongoRepository<Employee, String>
