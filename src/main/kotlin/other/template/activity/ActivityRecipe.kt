package other.template.activity

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import other.template.mvp.generateMVPContract
import other.template.mvp.generateMVPModel
import other.template.mvp.generateMVPPresenter
import other.template.mvp.generateMVPResponse
import other.template.utils.getModuleDir
import other.template.utils.save


fun RecipeExecutor.activityTemplateSetup(
    moduleData: ModuleTemplateData,
    mvpName: String,
    description: String,
    generateUI: Boolean,
    activityName: String,
    activityLayoutName: String,
    needRequestMap: Boolean,
    userObjectAsResponse: Boolean

) {
    addAllKotlinDependencies(moduleData)

    val packageName = moduleData.packageName

    val folderPath = "$packageName.mvp" // .$mvpName
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
        generateMVPActivity(packageName, mvpName, activityLayoutName, description, needRequestMap, userObjectAsResponse)
            .save(directorySrc, viewPath, "${activityName}.kt")
        // layout
        generateMVPActivityLayout()
            .save(directoryRes, layoutPath, "${activityLayoutName}.xml")
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