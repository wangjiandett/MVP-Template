package other.template.fragment

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import other.template.mvp.generateMVPContract
import other.template.mvp.generateMVPModel
import other.template.mvp.generateMVPPresenter
import other.template.mvp.generateMVPResponse
import other.template.utils.getModuleDir
import other.template.utils.save

fun RecipeExecutor.fragmentTemplateSetup(
        moduleData: ModuleTemplateData,
        mvpName: String,
        description: String,
        generateUI: Boolean,
        fragmentName: String,
        fragmentLayoutName: String,
        needRequestMap: Boolean,
        userObjectAsResponse: Boolean
) {
    addAllKotlinDependencies(moduleData)

    val packageName = moduleData.packageName

    val folderPath = "$packageName.mvp"
    val viewPath = "$folderPath.view"
    val layoutPath = "layout"
    val presenterPath = "$folderPath.presenter"
    val contractPath = "$folderPath.contract"
    val modelPath = "$folderPath.model"
    val responsePath = "$folderPath.bean"

    val moduleDir = getModuleDir(moduleData)
    val directorySrc = moduleDir.first ?: return
    val directoryRes = moduleDir.second ?: return

    //create and save file
    if(generateUI){
        // activity
        generateMVPFragment(packageName, mvpName, fragmentLayoutName, description, needRequestMap, userObjectAsResponse)
            .save(directorySrc, viewPath, "${fragmentName}.kt")
        // layout
        generateMVPFragmentLayout()
            .save(directoryRes, layoutPath, "${fragmentLayoutName}.xml")
    }

    // response
    if(!userObjectAsResponse){
        generateMVPResponse(packageName, mvpName, description)
            .save(directorySrc, responsePath, "${mvpName}Response.kt")
    }

    // contract
    generateMVPContract(packageName, mvpName, description, needRequestMap, userObjectAsResponse)
        .save(directorySrc, contractPath, "${mvpName}Contract.kt")

    // presenter
    generateMVPPresenter(packageName, mvpName, description, needRequestMap, userObjectAsResponse)
        .save(directorySrc, presenterPath, "${mvpName}Presenter.kt")

    // model
    generateMVPModel(packageName, mvpName, description, needRequestMap, userObjectAsResponse)
        .save(directorySrc, modelPath, "${mvpName}Model.kt")

}