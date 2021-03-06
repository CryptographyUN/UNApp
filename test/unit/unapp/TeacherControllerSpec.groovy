package unapp


import grails.test.mixin.*
import spock.lang.*

@TestFor(TeacherController)
@Mock(Teacher)
class TeacherControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["name"] = "Profesor"
        params["username"] = "profesor"
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        // TODO FIX
//        !model.teacherInstanceList
//        model.teacherInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.teacherInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def teacher = new Teacher()
        teacher.validate()
        controller.save(teacher)

        then: "The create view is rendered again with the correct model"
        model.teacherInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        teacher = new Teacher(params)

        controller.save(teacher)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/teacher/show/1'
        controller.flash.message != null
        Teacher.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        //controller.show(null)

        then: "A 404 error is returned"
        //response.status == 404

        when: "A domain instance is passed to the show action"
        //populateValidParams(params)
        //def teacher = new Teacher(params)
        //controller.show(teacher)

        then: "A model is populated containing the domain instance"
        //model.teacherInstance == teacher
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def teacher = new Teacher(params)
        controller.edit(teacher)

        then: "A model is populated containing the domain instance"
        model.teacherInstance == teacher
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/teacher/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def teacher = new Teacher()
        teacher.validate()
        controller.update(teacher)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.teacherInstance == teacher

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        teacher = new Teacher(params).save(flush: true)
        controller.update(teacher)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/teacher/show/$teacher.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/teacher/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def teacher = new Teacher(params).save(flush: true)

        then: "It exists"
        Teacher.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(teacher)

        then: "The instance is deleted"
        Teacher.count() == 0
        response.redirectedUrl == '/teacher/index'
        flash.message != null
    }


    void "Test that the search functionality in teachers is correct"(){
        when: "create diferent teachers with diferent names"
        def felipe_restrepo_calle = new Teacher()
        felipe_restrepo_calle.setName("FELIPE RESTREPO CALLE")
        felipe_restrepo_calle.setUsername(felipe_restrepo_calle.getName())
        felipe_restrepo_calle.save()

        def felipe_sua = new Teacher()
        felipe_sua.setName("FELIPE SUA")
        felipe_sua.setUsername(felipe_sua.getName())
        felipe_sua.save()

        def calle_juan = new Teacher()
        calle_juan.setName("CALLE JUAN")
        calle_juan.setUsername(calle_juan.getName())
        calle_juan.save()

        def rondon_mauricio = new Teacher()
        rondon_mauricio.setName("RONDON MAURICIO")
        rondon_mauricio.setUsername(rondon_mauricio.getName())
        rondon_mauricio.save()

        def restrepo_avellaneda_felipe = new Teacher()
        restrepo_avellaneda_felipe.setName("RESTREPO AVELLANEDA FELIPE")
        restrepo_avellaneda_felipe.setUsername(restrepo_avellaneda_felipe.getName())
        restrepo_avellaneda_felipe.save()

        then: "teachers are persisted"
        Teacher.count() == 5

        when: "search for felipe"
        def search_result = controller.search_by_keywords("felipe")

        then: "all felipes are found when search for felipe"
        search_result.contains(felipe_restrepo_calle)
        search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        search_result.contains(restrepo_avellaneda_felipe)

        when: "when search for FELIPE RESTREPO"
        search_result = controller.search_by_keywords("FELIPE RESTREPO")

        then: "returns those who have both names no matter the order"
        search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        search_result.contains(restrepo_avellaneda_felipe)

        when: "when search for calle FELIPE RESTREPO"
        search_result = controller.search_by_keywords("calle FELIPE RESTREPO")

        then: "return the correct set"
        search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        !search_result.contains(restrepo_avellaneda_felipe)

        when: "when search for CALLE"
        search_result = controller.search_by_keywords("calle")

        then: "return the correct set"
        search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        !search_result.contains(restrepo_avellaneda_felipe)

        when: "when search for rondon"
        search_result = controller.search_by_keywords("rondon")

        then: "return the correct set"
        !search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        search_result.contains(rondon_mauricio)
        !search_result.contains(restrepo_avellaneda_felipe)


        when: "when search for sua felipe"
        search_result = controller.search_by_keywords("sua felipe")

        then: "return the correct set"
        !search_result.contains(felipe_restrepo_calle)
        search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        !search_result.contains(restrepo_avellaneda_felipe)

        when: "when search for avellaneda"
        search_result = controller.search_by_keywords("avellaneda")

        then: "return the correct set"
        !search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        search_result.contains(restrepo_avellaneda_felipe)


        when: "when search for sanchez"
        search_result = controller.search_by_keywords("sanchez")

        then: "return the correct set"
        !search_result.contains(felipe_restrepo_calle)
        !search_result.contains(felipe_sua)
        !search_result.contains(calle_juan)
        !search_result.contains(rondon_mauricio)
        !search_result.contains(restrepo_avellaneda_felipe)

    }
}
























