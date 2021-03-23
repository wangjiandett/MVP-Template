package other.template.mvp

fun generateMVPResponse(
        packageName: String,
        mvpName: String,
        describe: String
) = """package ${packageName}.mvp.bean;

/**
 * ${describe}Response
 *
 * @author wangjian
 * Created on 2020/12/23 9:54
 */
class ${mvpName}Response {

}

"""