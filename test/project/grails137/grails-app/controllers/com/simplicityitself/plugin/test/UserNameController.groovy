package com.simplicityitself.plugin.test

class UserNameController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userNameInstanceList: UserName.list(params), userNameInstanceTotal: UserName.count()]
    }

    def create = {
        def userNameInstance = new UserName()
        userNameInstance.properties = params
        return [userNameInstance: userNameInstance]
    }

    def save = {
        def userNameInstance = new UserName(params)
        if (userNameInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userName.label', default: 'UserName'), userNameInstance.id])}"
            redirect(action: "show", id: userNameInstance.id)
        }
        else {
            render(view: "create", model: [userNameInstance: userNameInstance])
        }
    }

    def show = {
        def userNameInstance = UserName.get(params.id)
        if (!userNameInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userNameInstance: userNameInstance]
        }
    }

    def edit = {
        def userNameInstance = UserName.get(params.id)
        if (!userNameInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userNameInstance: userNameInstance]
        }
    }

    def update = {
        def userNameInstance = UserName.get(params.id)
        if (userNameInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userNameInstance.version > version) {
                    
                    userNameInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userName.label', default: 'UserName')] as Object[], "Another user has updated this UserName while you were editing")
                    render(view: "edit", model: [userNameInstance: userNameInstance])
                    return
                }
            }
            userNameInstance.properties = params
            if (!userNameInstance.hasErrors() && userNameInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userName.label', default: 'UserName'), userNameInstance.id])}"
                redirect(action: "show", id: userNameInstance.id)
            }
            else {
                render(view: "edit", model: [userNameInstance: userNameInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userNameInstance = UserName.get(params.id)
        if (userNameInstance) {
            try {
                userNameInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userName.label', default: 'UserName'), params.id])}"
            redirect(action: "list")
        }
    }
}
