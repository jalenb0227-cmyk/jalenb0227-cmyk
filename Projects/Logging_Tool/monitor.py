import os
import re


def scan_logs(log_file):
    # 1. Get the directory where monitor.py is located
    script_dir = os.path.dirname(os.path.abspath(__file__))
    
    # 2. Combine that directory path with your log filename
    full_path = os.path.join(script_dir, log_file)
    
    fail_pattern = r"Failed password"
    
    # 3. Open using the absolute path
    with open(full_path) as file:
        count = 0
        for line in file:
            count += 1
            if re.search(fail_pattern, line):
                
                print(f"⚠️  SECURITY ALERT: Suspicious activity detected in line {count}: {line.strip()}")

if __name__ == "__main__":
    scan_logs("mock_access.log")