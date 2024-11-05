package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeeRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(private val employeeRepository: EmployeeRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? =
        employeeRepository.findById(id).orElse(null)

    @QueryMapping
    fun employeeByEmail(@Argument email: String): Employee? =
        employeeRepository.findAll().find { it.email == email }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name ?: throw Exception("Name is required"),
            dateOfBirth = createEmployeeInput.dateOfBirth ?: throw Exception("Date of Birth is required"),
            city = createEmployeeInput.city ?: throw Exception("City is required"),
            salary = createEmployeeInput.salary ?: throw Exception("Salary is required"),
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employee.id = UUID.randomUUID().toString()
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id)
            true
        } else false
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument createEmployeeInput: CreateEmployeeInput): Employee? {
        val existingEmployee = employeeRepository.findById(id).orElse(null) ?: return null
        existingEmployee.apply {
            name = createEmployeeInput.name ?: name
            dateOfBirth = createEmployeeInput.dateOfBirth ?: dateOfBirth
            city = createEmployeeInput.city ?: city
            salary = createEmployeeInput.salary ?: salary
            gender = createEmployeeInput.gender ?: gender
            email = createEmployeeInput.email ?: email
        }
        return employeeRepository.save(existingEmployee)
    }
}
