SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
export JAVA_HOME="$SCRIPT_DIR/../.jdks/java-21/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
