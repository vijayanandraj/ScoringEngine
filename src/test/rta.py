import xml.etree.ElementTree as ET

def get_target_dotnet_version(csproj_file_path):
    try:
        tree = ET.parse(csproj_file_path)
        root = tree.getroot()
        target_framework = root.find("./{http://schemas.microsoft.com/developer/msbuild/2003}PropertyGroup/{http://schemas.microsoft.com/developer/msbuild/2003}TargetFramework")
        if target_framework is not None:
            return target_framework.text
    except ET.ParseError:
        print(f"Error parsing {csproj_file_path}")
    return None

if __name__ == "__main__":
    csproj_file_path = "path/to/your/csproj/file.csproj"
    target_version = get_target_dotnet_version(csproj_file_path)
    if target_version:
        print(f"Target .NET version: {target_version}")
    else:
        print("Target .NET version not found")
