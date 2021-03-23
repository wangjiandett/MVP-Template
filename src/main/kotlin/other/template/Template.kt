package other.template

import com.android.tools.idea.wizard.template.*
import other.template.activity.activityTemplateSetup
import other.template.fragment.fragmentTemplateSetup
import other.template.utils.TemplateType

val MVPTemplateData
    get() = template {
        revision = 2
        name = "MVPTemplate"
        description = "Create a new Dett MVP module from template"
        minApi = 16
        minBuildApi = 16
        category = Category.Other // Check other categories
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
            WizardUiContext.NewProject, WizardUiContext.NewModule
        )

        val mvpName = stringParameter {
            name = "MVPName"
            default = "MVP"
            help = "The name of the MVP files to create"
            constraints = listOf(Constraint.NONEMPTY, Constraint.CLASS, Constraint.UNIQUE)
        }

        val describe = stringParameter {
            name = "Description"
            default = mvpName.value
            suggest = { mvpName.value }
            help = "MVP function's description"
            constraints = listOf(Constraint.STRING)
        }

        val generateUI = booleanParameter {
            name = "generateUI"
            default = false
            help = "If true, a Fragment or Activity file will be generated else or not"
        }

        val templatePageType = enumParameter<TemplateType> {
            name = "Template Type"
            default = TemplateType.Activity
            help = "Template Type"
            visible = { generateUI.value }
        }

        //-------------activity------------

        val activityName = stringParameter {
            name = "Activity Name"
            default = "MVPActivity"
            help = "Activity name"
            constraints = listOf(Constraint.NONEMPTY)
            suggest = { layoutToActivity(mvpName.value) }
            visible = { templatePageType.value == TemplateType.Activity && generateUI.value }
        }

        val activityLayoutName = stringParameter {
            name = "Layout Name"
            default = "activity_${mvpName.value.toLowerCase()}"
            help = "The name of the layout to create for the activity"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { activityToLayout(activityName.value) }
            visible = { activityName.visible }
        }

        //-------------fragment------------

        val fragmentName = stringParameter {
            name = "Fragment Name"
            default = "MVPFragment"
            help = "Fragment name"
            constraints = listOf(Constraint.NONEMPTY)
            suggest = { layoutToFragment(mvpName.value) }
            visible = { templatePageType.value == TemplateType.Fragment && generateUI.value }
        }

        val fragmentLayoutName = stringParameter {
            name = "Layout Name"
            default = "fragment_mvp"
            help = "The name of the layout to create for the fragment"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { fragmentToLayout(mvpName.value) }
            visible = { fragmentName.visible }
        }


        //-------------mvp------------

        val needRequestMap = booleanParameter {
            name = "needRequestMap"
            default = false
            help = "If true, request params map will be added otherwise empty"
        }

        val userObjectAsResponse = booleanParameter {
            name = "userObjectAsResponse"
            default = false
            help = "If true, response params object will be added otherwise ${mvpName.value}Response"
        }

        widgets = listOf(// 通用模块
            TextFieldWidget(mvpName),
            TextFieldWidget(describe),
            CheckBoxWidget(generateUI),
            EnumWidget(templatePageType),

            // 界面模块
            TextFieldWidget(activityName),
            TextFieldWidget(activityLayoutName),
            TextFieldWidget(fragmentName),
            TextFieldWidget(fragmentLayoutName),

            // mvp模块
            CheckBoxWidget(needRequestMap),
            CheckBoxWidget(userObjectAsResponse)
        )

        recipe = { data: TemplateData ->
            if (templatePageType.value == TemplateType.Activity) {
                activityTemplateSetup(
                    data as ModuleTemplateData,
                    mvpName.value,
                    describe.value,
                    generateUI.value,
                    activityName.value,
                    activityLayoutName.value,
                    needRequestMap.value,
                    userObjectAsResponse.value
                )
            } else {
                fragmentTemplateSetup(
                    data as ModuleTemplateData,
                    mvpName.value,
                    describe.value,
                    generateUI.value,
                    fragmentName.value,
                    fragmentLayoutName.value,
                    needRequestMap.value,
                    userObjectAsResponse.value
                )
            }
        }
    }