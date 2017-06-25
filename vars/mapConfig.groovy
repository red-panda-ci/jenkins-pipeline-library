/**

  Global map

*/
public String env
public String projectName

def call () {
    this.env = env
    this.projectName = 'testProject'
    return this
}
