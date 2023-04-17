import zipfile
import os
from xml.etree import ElementTree

def print_target_value(file_data, file_name):
    try:
        tree = ElementTree.fromstring(file_data)
        target = tree.find("./target")
        if target is not None:
            print(f"File name: {file_name}\nTarget: {target.text}\n")
    except ElementTree.ParseError:
        print(f"Error parsing {file_name}")

def search_files_in_zip(zip_file_path):
    with zipfile.ZipFile(zip_file_path, 'r') as zip_ref:
        for file in zip_ref.infolist():
            if '/' in file.filename:  # Check if the file is inside a subfolder
                if file.filename.endswith('test.xml'):
                    with zip_ref.open(file, 'r') as file_ref:
                        file_data = file_ref.read().decode('utf-8')
                        print_target_value(file_data, file.filename)

if __name__ == "__main__":
    zip_file_path = "path/to/your/zipfile.zip"
    search_files_in_zip(zip_file_path)
