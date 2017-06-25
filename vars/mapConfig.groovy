/**

  Global map

*/
public String env
public String projectName

def call () {
    this.env = env
    this.projectName = 'testProject'
    echo 'Project config done'
    return this
}
