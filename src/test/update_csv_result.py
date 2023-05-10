import os
import csv
from pathlib import Path

# Function to find all solution files in a directory and its subdirectories
def find_solution_files(directory):
    solution_files = []
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith('.sln'):
                solution_files.append(os.path.join(root, file))
    return solution_files

# Function to create a project path to solution and project mapping
def create_project_mapping(solution_files):
    project_mapping = {}
    for sln_path in solution_files:
        solution = parse(sln_path)
        for project_file in solution.project_files():
            project_path = os.path.normpath(os.path.join(os.path.dirname(sln_path), project_file))
            project_mapping[project_path] = (sln_path, project_file)
    return project_mapping

def create_project_mapping_1(solution_files):
    project_mapping = {}
    for sln_path in solution_files:
        solution = parse(sln_path)
        for project_file in solution.project_files():
            project_path = os.path.normpath(os.path.join(os.path.dirname(sln_path), project_file))
            project_dir = os.path.dirname(project_path)

            # Scan the project directory and its subdirectories for source files
            for root, _, files in os.walk(project_dir):
                for file in files:
                    if file.endswith(('.cs', '.vb')):
                        source_file_path = os.path.normpath(os.path.join(root, file))
                        project_mapping[source_file_path] = (sln_path, project_file)
    return project_mapping

# Function to update the log CSV file with solution and project information
def update_csv_log(input_csv, output_csv, project_mapping):
    with open(input_csv, 'r', newline='', encoding='utf-8') as input_file, \
         open(output_csv, 'w', newline='', encoding='utf-8') as output_file:

        reader = csv.reader(input_file)
        writer = csv.writer(output_file)

        # Write the updated header to the output CSV
        header = next(reader)
        writer.writerow(['filename', 'solution_file', 'project_file'] + header[1:])

        # Process the log records and add the solution and project information
        for row in reader:
            fullpath = row[1]
            project_info = project_mapping.get(fullpath, ('Unknown', 'Unknown'))
            writer.writerow([row[0]] + list(project_info) + row[1:])

def add_orphaned_projects_to_mapping(root_directory, project_mapping):
    for root, _, files in os.walk(root_directory):
        for file in files:
            if file.endswith(('.csproj', '.vbproj')):
                project_path = os.path.normpath(os.path.join(root, file))
                project_dir = os.path.dirname(project_path)

                # Check if the project file is already in the mapping
                if project_path not in project_mapping.values():
                    for src_root, _, src_files in os.walk(project_dir):
                        for src_file in src_files:
                            if src_file.endswith(('.cs', '.vb')):
                                src_file_path = os.path.normpath(os.path.join(src_root, src_file))
                                project_mapping[src_file_path] = ('Unknown', project_path)

def add_orphaned_projects_to_mapping_1(root_directory, project_mapping):
    # Extract only project filenames from project_mapping
    project_files = {os.path.split(proj_path)[1] for sln_path, proj_path in project_mapping.values()}

    for root, _, files in os.walk(root_directory):
        for file in files:
            if file.endswith(('.csproj', '.vbproj')) and file not in project_files:
                project_path = os.path.normpath(os.path.join(root, file))
                project_dir = os.path.dirname(project_path)

                for src_root, _, src_files in os.walk(project_dir):
                    for src_file in src_files:
                        if src_file.endswith(('.cs', '.vb')):
                            src_file_path = os.path.normpath(os.path.join(src_root, src_file))
                            project_mapping[src_file_path] = ('Unknown', project_path)


# Main script
input_csv = 'log_statements.csv'
output_csv = 'log_statements_updated.csv'
root_folder = 'C:/Temp/BankProj'

solution_files = find_solution_files(root_folder)
project_mapping = create_project_mapping(solution_files)
add_orphaned_projects_to_mapping(root_folder, project_mapping)
update_csv_log(input_csv, output_csv, project_mapping)
