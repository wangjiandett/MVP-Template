package other.template.mvp

fun generateMVPModel(
    packageName: String,
    mvpName: String,
    describe: String,
    needRequestMap: Boolean,
    userObjectAsResponse: Boolean
): String {

    var request = ""
    var requestWithDivider = ""

    if (needRequestMap) {
        request = "request"
        requestWithDivider = "request: Map<String, String>, "
    }

    var import = ""
    var response = ""
    var resultResponse = ""

    if (!userObjectAsResponse) {
        import = "import ${packageName}.mvp.bean.${mvpName}Response"
        response = "${mvpName}Response"
        resultResponse = "response: ${mvpName}Response?"

    } else {
        import = ""
        response = "Any"
        resultResponse = "response: Any?"
    }

return """
package ${packageName}.mvp.model;

import com.moa.baselib.base.net.mvp.ValueCallback;
$import
import ${packageName}.mvp.contract.${mvpName}Contract;
import ${packageName}.net.ApiCreator
import ${packageName}.net.AppModel

/**
 * ${describe}Model
 *
 * Created by：wangjian on 2017/12/21 11:00
 */
class ${mvpName}Model : AppModel<${response}?>(), ${mvpName}Contract.I${mvpName}Model {

    override fun send${mvpName}Request(${requestWithDivider}callback: ValueCallback<${response}?>?) {
        mCallback = callback
        request(ApiCreator.apiInterfaces.send${mvpName}Request(${request}))
    }

    override fun onShowProgress() { // optional
        mCallback.onShowProgress()
    }

    override fun onHideProgress() { // optional
        mCallback.onHideProgress()
    }

    override fun onSuccess(${resultResponse}) {
        mCallback.onSuccess(response)
    }

    override fun onFail(code: Int, msg: String) {
        mCallback.onFail(code, msg)
    }
}
"""
}